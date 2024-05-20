export interface AddMatchRequest {
    matchDate: number | null,
    seasonId: number | null,
    tournamentId: number | null,
    homeTeamId: number | null,
    awayTeamId: number| null,
    homeTeamScore: string,
    awayTeamScore: string
}